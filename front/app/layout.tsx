// front/app/layout.jsx
"use client";
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import theme from "../theme";
import { AppContextProvider } from "@components/context/AppContext";
import { GlobalPage } from "@components/page";
import { AppRouterHandler } from "@components/router/AppRouterHandler";

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <AppContextProvider>
          <AppRouterHandler>
            <GlobalPage />
            <ThemeProvider theme={theme}>
              <CssBaseline />
              {children}
            </ThemeProvider>
          </AppRouterHandler>
        </AppContextProvider>
      </body>
    </html>
  );
}
