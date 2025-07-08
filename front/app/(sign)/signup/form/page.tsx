"use client";
import {
  Button,
  Card,
  FormControl,
  Grid,
  TextField,
  Typography,
} from "@mui/material";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { ApiService } from "@services/ApiService";

export function SignUpForms() {
  const [userEmail, setUserEmail] = useState("");
  const [userPassword, setUserPassword] = useState("");
  const [userPasswordConfirm, setUserPasswordConfirm] = useState("");
  const [errors, setErrors] = useState({
    email: "",
    password: "",
    passwordConfirm: "",
  });

  const route = useRouter();

  const handleSubmit = async () => {
    if (
      errors.email === "" &&
      errors.password === "" &&
      errors.passwordConfirm === ""
    ) {
      if (userPassword.length < 8 || userPassword.length > 32) {
        setErrors((prev) => ({
          ...prev,
          password: "Password must be between 8 and 32 characters",
        }));
        return;
      }
      const response = await ApiService.signUp({
        email: userEmail,
        password: userPassword,
      });

      if (response && response.status === 201) {
        route.push("/signin");
      }
    }
  };

  useEffect(() => {
    setErrors((prev) => ({
      ...prev,
      email: userEmail.length === 0 ? "Email cannot be empty" : "",
      password: userPassword.length === 0 ? "Password cannot be empty" : "",
      passwordConfirm:
        userPassword !== userPasswordConfirm ? "Passwords do not match" : "",
    }));
  }, [userEmail, userPassword, userPasswordConfirm]);

  return (
    <>
      <Grid
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "100vh",
        }}
      >
        <Card
          color="primary"
          variant="elevation"
          style={{ textAlign: "center" }}
        >
          <Typography style={{ margin: "center", fontSize: 25 }}>
            Sign In
          </Typography>
          <FormControl sx={{ m: 1, width: "50ch" }}>
            <TextField
              id="email"
              label="Email"
              variant="outlined"
              required
              size="small"
              margin="dense"
              onChange={(e) => setUserEmail(e.target.value)}
              error={errors.email !== ""}
              helperText={errors.email}
            />
            <TextField
              id="password"
              label="Password"
              type={"password"}
              variant="outlined"
              required
              size="small"
              margin="dense"
              onChange={(e) => setUserPassword(e.target.value)}
              error={errors.password !== ""}
              helperText={errors.password}
            />
            <TextField
              id="passwordConfirm"
              label="Insert the password one more time"
              type={"password"}
              variant="outlined"
              required
              size="small"
              margin="dense"
              onChange={(e) => setUserPasswordConfirm(e.target.value)}
              error={errors.passwordConfirm !== ""}
              helperText={errors.passwordConfirm}
            />

            <Button
              variant="contained"
              style={{ margin: "10px" }}
              onClick={handleSubmit}
            >
              Sign Up
            </Button>
          </FormControl>
        </Card>
      </Grid>
    </>
  );
}

export default SignUpForms;
