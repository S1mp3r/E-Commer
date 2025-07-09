import { Location } from "./Location";

export interface User {
  firstName: string;
  lastName: string;
  email: string;
  cellPhoneNumber: string;
  cpf: string;
  birthDate: string;
  locations: Location[];
}
