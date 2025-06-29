// front/app/layout.jsx
"use client";
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import theme from "../theme";
import { AppContextProvider } from "@components/context/AppContext";
import { GlobalPage } from "@components/login";

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <AppContextProvider>
          <GlobalPage />
          <ThemeProvider theme={theme}>
            <CssBaseline />
            {children}
          </ThemeProvider>
        </AppContextProvider>
      </body>
    </html>
  );
}
