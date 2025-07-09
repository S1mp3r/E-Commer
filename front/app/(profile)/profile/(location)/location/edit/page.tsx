"use client";
import { Location } from "@interfaces/Location";
import { Grid, TextField, Typography } from "@mui/material";

export function AddLocationPage({
  location,
  handleChange,
  handleSubmit,
}: {
  location: Location;
  handleChange: (field: string, value: string) => void;
  handleSubmit: () => void;
}) {
  console.log(location);
  return (
    <Grid>
      <TextField
        label="Endereço"
        onChange={(e) => handleChange("street", e.target.value)}
      />
      <Typography>
        <strong>Endereço:</strong>{" "}
        {location && location.street ? location.street : "-"}
      </Typography>
      <Typography>
        <strong>CEP:</strong> {location && location.cep ? location.cep : "-"}
      </Typography>
      <Typography>
        <strong>Cidade:</strong>{" "}
        {location && location.city ? location.city : "-"}
      </Typography>
      <Typography>
        <strong>Estado:</strong>{" "}
        {location && location.state ? location.state : "-"}
      </Typography>
      <Typography>
        <strong>País:</strong>{" "}
        {location && location.country ? location.country : "-"}
      </Typography>
      <Typography>
        <strong>Complemento:</strong>{" "}
        {location && location.complement ? location.complement : "-"}
      </Typography>
      <Typography>
        <strong>Número:</strong>{" "}
        {location && location.number ? location.number : "-"}
      </Typography>
      <Typography>
        <strong>Bairro:</strong>{" "}
        {location && location.neighborhood ? location.neighborhood : "-"}
      </Typography>
    </Grid>
  );
}

export default AddLocationPage;
