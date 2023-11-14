import { UserRole } from '../enums/user-role.enum';

export interface UserDto {
  name: string;
  username: string;
  role: UserRole;
}