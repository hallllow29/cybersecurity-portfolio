---
title: Projects
---

# Projects

Below is an auto-generated list of projects. Each project is a collection item under `_projects` (or a folder inside `docs/projects/`).

{% for p in site.projects %}
- **[{{ p.title }}]({{ p.url }})** — {{ p.excerpt | strip_html | truncatewords:20 }}
{% endfor %}
