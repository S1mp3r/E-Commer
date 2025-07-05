import { User } from "src/interfaces/User";
import { create } from "zustand";

interface UserStore {
  user: User;
  setUser: (user: User) => void;
  logout: () => void;
}

const initialUser = {
  firstName: "",
  lastName: "",
  email: "",
  cellPhoneNumber: "",
  cpf: "",
  birthDate: "",
};

export const useUserStore = create<UserStore>((set) => ({
  user: initialUser,
  setUser: (user) => set({ user }),
  logout: () => set({ user: initialUser }),
}));
