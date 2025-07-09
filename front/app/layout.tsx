// front/app/layout.jsx
"use client";
import { AppContextProvider } from "@components/context/AppContext";
import { GlobalPage } from "@components/page";
import { AppRouterHandler } from "@components/router/AppRouterHandler";
import ThemeWrapper from "@components/themes/ThemeWrapper";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <AppContextProvider>
          <AppRouterHandler>
            <GlobalPage />
            <ThemeWrapper>{children}</ThemeWrapper>
          </AppRouterHandler>
        </AppContextProvider>
      </body>
    </html>
  );
}
