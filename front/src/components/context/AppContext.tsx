"use client";
import { createContext, useEffect, useState } from "react";

interface AppContextType {
  bgColor: string;
  setBgColor: (color: string) => void;
  btnColor: string;
  setBtnColor: (color: string) => void;
  auth: boolean;
  setAuth: (value: boolean) => void;
  isLoading: boolean;
}

export const AppContext = createContext<AppContextType | null>(null);

export const AppContextProvider = ({
  children,
}: {
  children: React.ReactNode;
}) => {
  const [bgColor, setBgColor] = useState(
    JSON.parse(localStorage.getItem("prefs"))?.bgColor || "#ffffff"
  );
  const [btnColor, setBtnColor] = useState(
    JSON.parse(localStorage.getItem("prefs"))?.btnColor || "#1976d2"
  );
  const [auth, setAuth] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  return (
    <AppContext.Provider
      value={{
        bgColor,
        setBgColor,
        btnColor,
        setBtnColor,
        auth,
        setAuth,
        isLoading,
      }}
    >
      {children}
    </AppContext.Provider>
  );
};
