import { TaskStatus } from "./task-status.model";

export type Board = {
  boardName?: string;
  taskStatuses?: TaskStatus[];
}