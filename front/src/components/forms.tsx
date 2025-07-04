// front/app/first-page.tsx.
"use client";
import { Box, Button, Grid } from "@mui/material";
import React from "react";
import { useRouter } from "next/navigation";
import "../../app/styles/globals.css";

export default function Page() {
  const router = useRouter();

  const handleClickSignIn = () => {
    router.push("/signin");
  };

  const handleClickSignUp = () => {
    router.push("/signup");
  };

  return (
    <Box sx={{ flexGrow: 0 }}>
      <Grid container>
        <Button
          style={{ margin: "10px" }}
          variant="contained"
          color="primary"
          onClick={handleClickSignIn}
        >
          Sign In
        </Button>
        <Button
          style={{ margin: "10px" }}
          variant="contained"
          color="primary"
          onClick={handleClickSignUp}
        >
          Sign Up
        </Button>
      </Grid>
    </Box>
  );
}
