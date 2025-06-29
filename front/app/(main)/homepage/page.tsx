"use client";
import React, { useContext } from "react";
import { ApiService } from "@services/ApiService";
import { Button } from "@mui/material";
import { AppContext } from "@components/context/AppContext";
import { Api } from "@mui/icons-material";

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
