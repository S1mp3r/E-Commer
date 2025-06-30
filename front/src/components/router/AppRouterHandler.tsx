"use client";
import { BaseUrl } from "@services/BaseUrl";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export const AppRouterHandler = ({ children }) => {
  const router = useRouter();

  useEffect(() => {
    BaseUrl.setOnError(() => {
      router.push("/signin");
    });
  }, []);

  return <>{children}</>;
};

export default AppRouterHandler;
