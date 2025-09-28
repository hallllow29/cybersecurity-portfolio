#!/usr/bin/env bash
set -e

REPO_OWNER="hallllow29"
REPO_NAME="cybersecurity-portfolio"
IGNORED="docs .github .git .vscode node_modules assets"

mkdir -p docs/projects

# Create/overwrite projects index
cat > docs/projects/index.md <<'MD'
---
title: Projects
---

# Projects

Below are the repository folders. Click a project to read its README as a page.
MD

for d in */ ; do
  name="${d%/}"
  skip=false
  for i in $IGNORED; do
    if [ "$name" = "$i" ]; then skip=true; break; fi
  done
  if [ "$skip" = true ]; then continue; fi

  target="docs/projects/$name"
  mkdir -p "$target"

  if [ -f "$name/README.md" ]; then
    echo "---" > "$target/index.md"
    echo "title: $name" >> "$target/index.md"
    echo "repo: https://github.com/${REPO_OWNER}/${REPO_NAME}/tree/main/${name}" >> "$target/index.md"
    echo "---" >> "$target/index.md"
    echo "" >> "$target/index.md"
    sed '1,20000p' "$name/README.md" >> "$target/index.md"
  else
    cat > "$target/index.md" <<EOF
---
title: $name
repo: https://github.com/$\{REPO_OWNER\}/$\{REPO_NAME\}/tree/main/$\{name\}
---

This project folder is available in the repository: [${name}](https://github.com/${REPO_OWNER}/${REPO_NAME}/tree/main/${name}).

Add a README.md inside the folder to show content here.
EOF
  fi

  echo "Generated docs/projects/$name/index.md"
done

# create a docs index if not present
if [ ! -f docs/index.md ]; then
  cat > docs/index.md <<'MD'
---
title: Home
---

# Welcome

This site shows the projects and CTF writeups stored in this repository.

- [Projects](/projects/)
MD
fi

echo "Done generating docs from repo folders."
