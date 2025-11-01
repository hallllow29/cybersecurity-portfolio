const User = require("../../models/Accounts/User");
const bcrypt = require("bcrypt");

async function autenticarUser(email, password) {
  const user = await User.findOne({ email: email.toLowerCase() });
  if (!user) throw new Error("Email ou password incorretos.");

  const passwordOk = await bcrypt.compare(password, user.password || "");
  if (!passwordOk) throw new Error("Email ou password incorretos.");

  return user;
}

module.exports = { autenticarUser };
