const mongoose = require("mongoose");

const UserSchema = new mongoose.Schema({
  email: {
    type: String,
    required: true,
    unique: true,
  },
  password: {
    type: String,
    required: true,
  },
  role: {
    type: String,
    enum: ["cliente", "admin", "restaurante"],
    default: "user",
  },
  refId: {
    type: mongoose.Schema.Types.ObjectId,
    default: null,
  },
});

module.exports = mongoose.models.User || mongoose.model("User", UserSchema);
