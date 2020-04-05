package codeuz.repository;

import codeuz.Main;
import codeuz.dto.TodoItem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TodoRepository {

    private Map<Long, List<TodoItem>> todoMap = new HashMap<>();


    public int add(Long userId, TodoItem todoItem) {
        if (todoMap.containsKey(userId)) {
            todoMap.get(userId).add(todoItem);
            return todoMap.get(userId).size();
        } else {
            List<TodoItem> list = new LinkedList<>();
            list.add(todoItem);
            todoMap.put(userId, list);
            return 1;
        }
    }

    public List<TodoItem> getTodoList(Long userId) {
        if (todoMap.containsKey(userId)) {
            return todoMap.get(userId);
        }
        return null;
    }


    public TodoItem getItem(Long userId, String id) {
        if (todoMap.containsKey(userId)) {
            List<TodoItem> list = todoMap.get(userId);
            for (TodoItem todoItem : list) {
                if (todoItem.getId().equals(id)) {
                    return todoItem;
                }
            }

        }
        return null;
    }

    public boolean delete(Long userId, String id) {
        TodoItem todoItem = this.getItem(userId, id);
        if (todoItem != null) {
            this.todoMap.get(userId).remove(todoItem);
            return true;
        }
        return false;
    }

}
