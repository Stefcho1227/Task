package org.example.taskproject.servicetests;

import org.example.taskproject.enums.Priority;
import org.example.taskproject.models.Task;
import org.example.taskproject.repositories.TaskRepository;
import org.example.taskproject.services.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImpl taskService;
    private Task sampleTask;
    @BeforeEach
    void setUp() {
        sampleTask = new Task();
        sampleTask.setId(1);
        sampleTask.setTitle("Sample Task");
        sampleTask.setDescription("Some description");
        sampleTask.setDueDate(LocalDate.now().plusDays(5));
        sampleTask.setCompleted(false);
        sampleTask.setCritical(false);
    }
    @Test
    void testCreateTask_PriorityCalculation() {

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Task created = taskService.create(sampleTask);
        verify(taskRepository, times(1)).save(any(Task.class));
        assertNotNull(created);
        assertEquals(Priority.MEDIUM, created.getPriority()); // 5 days until due => MEDIUM
    }
    @Test
    void testGetTaskById_Found() {
        when(taskRepository.findById(1))
                .thenReturn(Optional.of(sampleTask));

        Optional<Task> result = taskService.getTaskById(1);
        assertTrue(result.isPresent());
        assertEquals("Sample Task", result.get().getTitle());
    }
    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(999))
                .thenReturn(Optional.empty());

        Optional<Task> result = taskService.getTaskById(999);
        assertFalse(result.isPresent());
    }
    @Test
    void testUpdateTask_Success() {
        when(taskRepository.findById(1))
                .thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Title");
        updatedTask.setDescription("New Description");
        updatedTask.setDueDate(LocalDate.now().plusDays(2));  // triggers HIGH priority
        updatedTask.setCritical(false);
        updatedTask.setCompleted(false);
        Task result = taskService.updateTask(1, updatedTask);
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("New Description", result.getDescription());
        assertEquals(Priority.HIGH, result.getPriority());
        verify(taskRepository, times(1)).save(any(Task.class));
    }
    @Test
    void testUpdateTask_NotFound() {
        when(taskRepository.findById(999))
                .thenReturn(Optional.empty());
        Task updatedTask = new Task();
        assertThrows(ResponseStatusException.class, () -> {
            taskService.updateTask(999, updatedTask);
        });
    }
    @Test
    void testDeleteTask_Success() {
        when(taskRepository.findById(1))
                .thenReturn(Optional.of(sampleTask));
        taskService.deleteTask(1);
        verify(taskRepository, times(1)).delete(sampleTask);
    }
    @Test
    void testDeleteTask_NotFound() {
        when(taskRepository.findById(999))
                .thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> {
            taskService.deleteTask(999);
        });
        verify(taskRepository, never()).delete((Task)any());
    }
    @Test
    void testGetAllTasks_FilterByPriority_AndIsCompleted() {
        Task t1 = new Task();
        t1.setId(1);
        t1.setPriority(Priority.HIGH);
        t1.setCompleted(false);
        Task t2 = new Task();
        t2.setId(2);
        t2.setPriority(Priority.HIGH);
        t2.setCompleted(false);
        when(taskRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(Arrays.asList(t1, t2));
        List<Task> result = taskService.getAllTasks(null, null, Priority.HIGH, false, "priority", "asc");
        assertEquals(2, result.size());
        assertEquals(Priority.HIGH, result.get(0).getPriority());
        assertFalse(result.get(0).isCompleted());
    }
    @Test
    void testGetAllTasks_InvalidSortBy() {
        assertThrows(ResponseStatusException.class, () -> {
            taskService.getAllTasks(null, null, null, null, "banana", "asc");
        });
    }
    @Test
    void testGetAllTasks_SortDescendingDueDate() {
        Task t1 = new Task();
        t1.setDueDate(LocalDate.now().plusDays(1));
        Task t2 = new Task();
        t2.setDueDate(LocalDate.now().plusDays(3));
        when(taskRepository.findAll(any(Specification.class), any(Sort.class)))
                .thenReturn(Arrays.asList(t2, t1)); // simulate descending order
        List<Task> result = taskService.getAllTasks(null, null, null, null, "dueDate", "desc");
        assertEquals(2, result.size());
        assertEquals(t2, result.get(0));
        assertEquals(t1, result.get(1));
        ArgumentCaptor<Sort> sortCaptor = ArgumentCaptor.forClass(Sort.class);
        verify(taskRepository).findAll(any(Specification.class), sortCaptor.capture());
        Sort usedSort = sortCaptor.getValue();
        assertTrue(usedSort.toString().contains("dueDate: DESC"),
                "Expected dueDate descending sort but found: " + usedSort);
    }
    @Test
    void testComputePriority_CompletedTask() {
        sampleTask.setCompleted(true);
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Task created = taskService.create(sampleTask);
        assertEquals(Priority.LOW, created.getPriority());
    }
    @Test
    void testComputePriority_CriticalTask() {
        sampleTask.setCritical(true);
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Task created = taskService.create(sampleTask);
        assertEquals(Priority.HIGH, created.getPriority());
    }
    @Test
    void testComputePriority_DueDateWithin2Days() {
        sampleTask.setDueDate(LocalDate.now().plusDays(2));
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Task created = taskService.create(sampleTask);
        assertEquals(Priority.HIGH, created.getPriority());
    }
    @Test
    void testComputePriority_DueDateWithin5Days() {
        sampleTask.setDueDate(LocalDate.now().plusDays(5));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));
        Task created = taskService.create(sampleTask);
        assertEquals(Priority.MEDIUM, created.getPriority());
    }
    @Test
    void testComputePriority_DueDateMoreThan7Days() {
        sampleTask.setDueDate(LocalDate.now().plusDays(10));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));
        Task created = taskService.create(sampleTask);
        assertEquals(Priority.LOW, created.getPriority());
    }
}
