"use client";
import { Button } from "@mui/material";
import React from "react";
import { useRouter } from "next/navigation";

export default function Page() {
  const router = useRouter();

  const handleClickSignIn = () => {
    router.push("/signin"); // navega para /signin
  };

  const handleClickSignUp = () => {
    router.push("/signup"); // caso tenha a rota de cadastro em /signup
  };

  return (
    <>
      <h1>Welcome to Next.js!</h1>
      <Button
        style={{ margin: "10px" }}
        variant="contained"
        color="primary"
        onClick={handleClickSignIn}
      >
        SignIn
      </Button>
      <Button
        style={{ margin: "10px" }}
        variant="contained"
        color="primary"
        onClick={handleClickSignUp}
      >
        SignUp
      </Button>
    </>
  );
}
