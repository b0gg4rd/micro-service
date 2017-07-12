package net.coatli.java.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.coatli.java.domain.Person;
import net.coatli.java.event.CreatePersonEvent;
import net.coatli.java.event.PersonCreatedEvent;
import net.coatli.java.event.PersonDeletedEvent;
import net.coatli.java.event.PersonUpdatedEvent;
import net.coatli.java.event.RequestAllPersonsEvent;
import net.coatli.java.event.ResponseAllPersonsEvent;
import net.coatli.java.event.ResponsePersonEvent;
import net.coatli.java.event.UpdatePersonEvent;
import net.coatli.java.mapper.PersonMapper;

@RestController
public class PersonMicroService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonMicroService.class);

  private final PersonMapper personMapper;

  @Autowired
  public PersonMicroService(final PersonMapper personMapper) {
    this.personMapper = personMapper;
  }

  @PostMapping("/persons/")
  public ResponseEntity<PersonCreatedEvent> create(@RequestBody final CreatePersonEvent createPersonEvent) {

    LOGGER.info("Creating person {}", createPersonEvent.getPerson());

    personMapper.create(createPersonEvent.getPerson().setKey(UUID.randomUUID().toString()));

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new PersonCreatedEvent().setPerson(createPersonEvent.getPerson()));
  }

  @GetMapping("/persons/{key}")
  public ResponseEntity<ResponsePersonEvent> retrieve(@PathVariable final String key) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ResponsePersonEvent().setPerson(personMapper.retrieve(key)));
  }

  @PutMapping("/persons/{key}")
  public ResponseEntity<PersonUpdatedEvent> update(final UpdatePersonEvent updatePersonEvent) {

    personMapper.update(updatePersonEvent.getPerson());

    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(null);
  }

  @DeleteMapping("/persons/{key}")
  public ResponseEntity<PersonDeletedEvent> delete(@PathVariable final String key) {

    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(null);
  }

  @GetMapping("/persons/")
  public ResponseEntity<ResponseAllPersonsEvent> findAll() {

    final List<Person> persons = personMapper.findAll();

    return ResponseEntity
        .status(persons.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
        .body(new ResponseAllPersonsEvent().setPersons(persons));
  }

  @GetMapping("/persons")
  public ResponseEntity<ResponseAllPersonsEvent> findBy(final RequestAllPersonsEvent requestAllPersonsEvent) {

    final List<Person> persons = personMapper.findBy(requestAllPersonsEvent);

    return ResponseEntity
        .status(persons.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
        .body(new ResponseAllPersonsEvent().setPersons(persons));
  }

}
