package com.example.web_with_lombok.RestController;


import com.example.web_with_lombok.Entitiy.ToDo;
import com.example.web_with_lombok.Interfaces.CommonRepository;
import com.example.web_with_lombok.Validation.ToDoValidationError;
import com.example.web_with_lombok.Validation.ToDoValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private CommonRepository<ToDo> repository;

    @Autowired
    public ToDoController(CommonRepository<ToDo> repository) {
        this.repository = repository;
    }

    @GetMapping("/todo")
    ResponseEntity< Iterable<ToDo>> getToDos () {
        return  ResponseEntity.ok(repository.findAll());
    }
    @GetMapping("/todo/{id}")
      public ResponseEntity<ToDo> getToDoById(@PathVariable String id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    @PatchMapping ("/todo/{id}")
    public ResponseEntity<ToDo> setCompleted (
            @PathVariable String id ) {
        ToDo result = repository.findById(id);
        result.setCompeted(true);
        repository.save(result);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                buildAndExpand(result.getId()).toUri();
        return ResponseEntity.ok().header("Location" , location.toString()).build();

    }

    @RequestMapping(value="/todo", method = {
            RequestMethod.POST,
            RequestMethod.PUT})
    public ResponseEntity<?> createToDo(@Valid @RequestBody ToDo toDo,
                                        Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        ToDo result = repository.save(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo){
        repository.delete(toDo);
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        return new ToDoValidationError(exception.getMessage());
    }
}
