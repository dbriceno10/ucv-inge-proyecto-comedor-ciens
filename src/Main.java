import Controllers.TodoController;
import Model.TodoModel;
import View.TodoView;

public class Main {
  public static void main(String[] args) {
    TodoModel model = new TodoModel();
    TodoView view = new TodoView();
    TodoController controller = new TodoController(model, view);
    controller.init();
  }
}