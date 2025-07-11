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
  const [bgColor, setBgColor] = useState("#ffffff");
  const [btnColor, setBtnColor] = useState("#1976d2");
  const [auth, setAuth] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    // Nao lembro pq fiz isso, mas acho q era por conta do LocalStorage ta sendo acessado no SSR
    if (typeof window !== "undefined") {
      const prefs = JSON.parse(localStorage.getItem("prefs") || "{}");
      if (prefs.bgColor) setBgColor(prefs.bgColor);
      if (prefs.btnColor) setBtnColor(prefs.btnColor);
    }
  }, []);

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
