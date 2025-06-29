"use client";

import { useEffect, useContext } from "react";
import { AppContext } from "@/components/context/AppContext";

export function GlobalPage() {
  const { notify, error, isLoading } = useContext(AppContext);

  if (isLoading) {
    return (
      <div className="fixed top-0 w-full bg-gray-100 text-center">
        Loading...
      </div>
    );
  }

  return null;
}
