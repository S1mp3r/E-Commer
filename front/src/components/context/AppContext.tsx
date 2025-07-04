"use client";
import { createContext, useState } from "react";

export const AppContext = createContext(null);

export function AppContextProvider({ children }) {
  const [notify, setNotify] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [auth, setAuth] = useState(false);

  return (
    <AppContext.Provider
      value={{
        notify,
        setNotify,
        error,
        setError,
        isLoading,
        setIsLoading,
        auth,
        setAuth,
      }}
    >
      {children}
    </AppContext.Provider>
  );
}
