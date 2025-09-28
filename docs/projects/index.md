---
title: Projects
---

# Projects

Below is an auto-generated list of project pages.

{% assign projects = site.pages | where_exp:"p","p.url contains '/projects/'" %}
{% for p in projects %}
- [{{ p.title }}]({{ p.url }}) — {{ p.excerpt | strip_html | truncatewords:20 }}
{% endfor %}
