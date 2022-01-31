Set up codecov token and codecov badge.

## Acceptance criteria

- [ ] The repo is enabled on codecov on the `main` branch.
- [ ] The `CODECOV_TOKEN` for the repo is uploaded to the `Secrets`->`Actions`->`Action Secrets`.
- [ ] A markdown style badge for coverage on the `main` branch appears near the top of the README.md, and shows accurate information about coverage on the main branch.

# Details 

1. Visit the site for the repo, which is at this URL
   (note that you'll have to edit this URL to match your repo). You may
   need to log in first.

   * <https://app.codecov.io/gh/ucsb-cs156-w22/team02-w22-6pm-4/settings>

2. On that page, you'll see a place to set the default branch.  Be sure
   it is set to `main`.

3. On that same page, you'll see the *repository upload token*.  Copy
   that value and store it in the repo Action Secrets as `CODECOV_TOKEN`.

4. On that same page, at left you'll see a menu item called `Badge`.
   
   Go to that page, and copy the Markdown for the badge.  Put it in your
   README.md.  For this, it's ok to just edit the README.md directly 
   instead of doing a PR; or you can make a branch and do a PR for
   practice (your choice.)
