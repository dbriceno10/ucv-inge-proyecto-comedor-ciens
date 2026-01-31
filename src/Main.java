import todolist.controllers.TodoController;
import todolist.model.TodoModel;
import todolist.view.TodoView;

public class Main {
    public static void main(String[] args) {
        TodoModel model = new TodoModel();
        TodoView view = new TodoView();
        TodoController controller = new TodoController(model, view);
        controller.init();
    }
}