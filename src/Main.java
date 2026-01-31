import com.todolist.controllers.TodoController;
import com.todolist.model.TodoModel;
import com.todolist.view.TodoView;

public class Main {
    public static void main(String[] args) {
        TodoModel model = new TodoModel();
        TodoView view = new TodoView();
        TodoController controller = new TodoController(model, view);
        controller.init();
    }
}