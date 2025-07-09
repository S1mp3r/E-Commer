"use client";
import {
  Button,
  Card,
  FormControl,
  Grid,
  InputAdornment,
  TextField,
  Typography,
  Box,
} from "@mui/material";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { ApiService } from "@services/ApiService";
import EmailIcon from "@mui/icons-material/Email";
import HttpsIcon from "@mui/icons-material/Https";

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
    <Grid
      container
      justifyContent="center"
      alignItems="center"
      style={{ height: "100vh" }}
    >
      <Card
        elevation={3}
        sx={{
          padding: 4,
          minWidth: 400,
          maxWidth: 450,
        }}
      >
        <Box mb={2}>
          <Typography variant="h5" align="center">
            Sign Up
          </Typography>
        </Box>
        <FormControl fullWidth>
          <TextField
            id="email"
            label="Email"
            variant="outlined"
            required
            onChange={(e) => setUserEmail(e.target.value)}
            error={errors.email !== ""}
            helperText={errors.email}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <EmailIcon />
                </InputAdornment>
              ),
            }}
            sx={{ mb: 2 }}
          />
          <TextField
            id="password"
            label="Password"
            type="password"
            variant="outlined"
            required
            onChange={(e) => setUserPassword(e.target.value)}
            error={errors.password !== ""}
            helperText={errors.password}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <HttpsIcon />
                </InputAdornment>
              ),
            }}
            sx={{ mb: 2 }}
          />
          <TextField
            id="passwordConfirm"
            label="Insert the password one more time"
            type="password"
            variant="outlined"
            required
            onChange={(e) => setUserPasswordConfirm(e.target.value)}
            error={errors.passwordConfirm !== ""}
            helperText={errors.passwordConfirm}
            InputProps={{
              startAdornment: (
                <InputAdornment position="start">
                  <HttpsIcon />
                </InputAdornment>
              ),
            }}
            sx={{ mb: 2 }}
          />
          <Button
            variant="contained"
            onClick={handleSubmit}
            fullWidth
            sx={{ mt: 1 }}
          >
            Sign Up
          </Button>
        </FormControl>
      </Card>
    </Grid>
  );
}

export default SignUpForms;
