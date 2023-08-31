import { Board } from "../models/kanban/board.model";
import { Task } from "../models/kanban/task.model";

export interface DialogData {
  kanbanId: string;
  memberEmail: string;
  board: Board;
  task: Task
  taskStatusName: string;
  title: string;
  description: string;
  subtasks: string;
  createDate: Date;
  dueDate: Date;
  priority: string;
  assignTo: string;
  assignBy: string;
  color: string;
}
