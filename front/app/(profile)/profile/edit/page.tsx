"use client";
import { Box, TextField, Button, Grid } from "@mui/material";
import { User } from "@interfaces/User";

export default function EditProfilePage({
  form,
  handleChange,
  handleSubmit,
}: {
  form: User;
  handleChange: (field: string, value: string) => void;
  handleSubmit: () => void;
}) {
  return (
    <Box>
      <Box className="p-6 max-w-2xl mx-auto space-y-4">
        <Grid container sx={{ p: 1 }}>
          <TextField
            label="Nome"
            value={form.firstName}
            onChange={(e) => handleChange("firstName", e.target.value)}
          />
          <TextField
            label="Sobrenome"
            value={form.lastName}
            onChange={(e) => handleChange("lastName", e.target.value)}
          />
        </Grid>
        <TextField
          label="Email"
          fullWidth
          value={form.email}
          onChange={(e) => handleChange("email", e.target.value)}
          sx={{ p: 1 }}
        />
        <Grid sx={{ p: 1 }} spacing={2}>
          <TextField
            label="CPF"
            value={form.cpf}
            onChange={(e) => handleChange("cpf", e.target.value)}
          />
          <TextField
            label="Telefone"
            value={form.cellPhoneNumber}
            onChange={(e) => handleChange("cellPhoneNumber", e.target.value)}
          />
        </Grid>
        <Grid sx={{ p: 1 }} spacing={2}>
          <TextField
            label="Data de Nascimento"
            type="date"
            InputLabelProps={{ shrink: true }}
            value={form.birthDate}
            onChange={(e) => handleChange("birthDate", e.target.value)}
          />
        </Grid>
      </Box>
      <Button variant="contained" color="primary" onClick={handleSubmit}>
        Salvar
      </Button>
    </Box>
  );
}
