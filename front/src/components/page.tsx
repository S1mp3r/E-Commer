"use client";
import { useContext } from "react";
import { AppContext } from "@components/context/AppContext";

export function GlobalPage() {
  const { isLoading } = useContext(AppContext);

  if (isLoading) {
    return (
      <div className="fixed top-0 w-full bg-gray-100 text-center">
        Loading...
      </div>
    );
  }

  return null;
}
