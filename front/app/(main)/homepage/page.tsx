"use client";
import React from "react";
import { Button } from "@mui/material";
import { ApiService } from "@services/ApiService";

export default function HomePage() {

    const handleSubmit = () => {
      ApiService.test()
    };

  return (
    <>
      <h1>Welcome to my Application!</h1>
      <Button variant="contained" onClick={() => handleSubmit()}>
        Search
      </Button>
    </>
  );
}
