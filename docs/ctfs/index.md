---
title: CTFs
---

# CTFs

This page lists the CTFs and their challenges.

{% for c in site.ctfs %}
## [{{ c.title }}]({{ c.url }})
{{ c.excerpt | strip_html | truncatewords:30 }}

{% endfor %}
