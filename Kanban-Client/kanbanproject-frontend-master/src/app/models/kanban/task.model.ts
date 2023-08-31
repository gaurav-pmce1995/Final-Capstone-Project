export type Task = {
  title?: string;
  description?: string;
  subtasks?: string[];
  createDate?: Date;
  dueDate?: Date | null;
  priority?: string;
  assignTo?: string[];
  assignBy?: string;
  color?: string;
}