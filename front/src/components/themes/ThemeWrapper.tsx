// @components/theme/ThemeWrapper.tsx
"use client";
import { useContext } from "react";
import { ThemeProvider, createTheme, CssBaseline } from "@mui/material";
import { AppContext } from "@components/context/AppContext";

export default function ThemeWrapper({
  children,
}: {
  children: React.ReactNode;
}) {
  const context = useContext(AppContext);
  if (!context) return null;

  const { bgColor, btnColor } = context;

  const theme = createTheme({
    palette: {
      primary: {
        main: btnColor || "#1976d2",
      },
      secondary: {
        main: "#dc004e",
      },
      background: {
        default: bgColor || "#ffffff",
      },
    },
  });

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      {children}
    </ThemeProvider>
  );
}
