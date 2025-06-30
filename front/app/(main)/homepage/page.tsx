"use client";
import React from "react";
import { Button } from "@mui/material";

export default function HomePage() {
  return (
    <>
      <h1>Welcome to my Application!</h1>
      <Button variant="contained" onClick={() => console.log("clicked!")}>
        Search
      </Button>
    </>
  );
}
