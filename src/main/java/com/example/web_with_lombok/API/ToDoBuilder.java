package com.example.web_with_lombok.API;
import  com.example.web_with_lombok.Entitiy.ToDo;

public class ToDoBuilder {
    private  static ToDoBuilder instance = new ToDoBuilder();
    private  String id = null;
    private  String description="";

    public ToDoBuilder() {
    }

    private  static  ToDoBuilder create() {
        return  instance;
    }

    public  ToDoBuilder withDescription( String description)  {
        this.description = description;
        return instance;
    }
    public  ToDoBuilder withId( String id)  {
        this.id = id;
        return instance;
    }

    public ToDo build() {
        ToDo  result = new ToDo(this.description);
         if ( id != null)  result.setId(id);

         return  result;

    }
}
