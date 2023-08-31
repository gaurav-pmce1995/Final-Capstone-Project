import { Board } from "./board.model";
export type Kanban = {
  id?: string;
  email?: string;
  kanbanName?: string;
  boards?: Board[];
  teamMembers?: string[];
}
