package com.wander.services.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.wander.entities.Note;
import com.wander.entities.User;
import com.wander.exceptions.ResourceNotFoundException;
import com.wander.repositories.NoteRepository;
import com.wander.services.NoteServiceImpl;
import com.wander.services.UserServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
public class NotesServiceTest {

	@InjectMocks
	NoteServiceImpl noteService;

	NoteServiceImpl noteServiceSpy;

	@Mock
	UserServiceImpl userService;

	@Mock
	NoteRepository noteRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		noteServiceSpy = Mockito.spy(noteService);
	}

	@Test
	public void addNotesTest() {
		Note note = new Note();
		noteService.addNotes(note, "");

		verify(noteRepository, times(1)).save(note);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void updateNotesTest() throws ResourceNotFoundException {
		Note note = Mockito.mock(Note.class);
		noteService.updateNotes(anyLong(), note);
		when(noteRepository.findById(anyLong())).thenReturn(null);
		verify(noteRepository, times(1)).save(note);
	}

	@Test
	public void updateNotesWithResourceNotFoundTest() throws ResourceNotFoundException {
		Note note = new Note();
		doReturn(Optional.of(note)).when(noteRepository).findById(anyLong());
		noteService.updateNotes(anyLong(), note);
		verify(noteRepository, times(1)).save(note);
	}

	@Test
	public void viewAllNotesTest() throws ResourceNotFoundException {
		List<Note> notes = new ArrayList<>();
		doReturn(Optional.of(notes)).when(noteRepository).findById(anyLong());
		assertEquals(notes, noteService.viewAllNotes());
	}

	@Test
	public void deleteNoteTest() throws ResourceNotFoundException {
		Note note = new Note();
		note.setId(1L);
		doReturn(Optional.of(note)).when(noteRepository).findById(1L);
		noteService.deleteNotes(1L);
		verify(noteRepository, times(1)).delete(note);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void deleteNoteWithResourceNotFoundTest() throws ResourceNotFoundException {
		noteService.deleteNotes(anyLong());
		when(noteRepository.findById(anyLong())).thenReturn(null);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void findByIdResourceNotFoundExceptionTest() throws ResourceNotFoundException {
		noteService.findById(anyLong());
		when(noteRepository.findById(anyLong())).thenReturn(null);
	}

	@Test
	public void findByIdTest() throws ResourceNotFoundException {
		Note note = new Note();
		note.setId(1L);
		doReturn(Optional.of(note)).when(noteRepository).findById(1L);
		assertEquals(note, noteService.findById(1L));
	}
	
	@Test
	public void findByUserTest() throws ResourceNotFoundException {
		List<Note> notes = new ArrayList<>();
		doReturn(notes).when(noteRepository).findByUser(any(User.class));
		assertEquals(notes, noteService.findByUser(new User()));
	}
}
