
# Make Pull Requests
https://services.github.com/on-demand/github-cli/open-pull-request-github

Use the command line to make the pull-request:

Push your commits to the remote, and set a tracking branch. Type:
```bash
1. git push -u origin <BRANCH-NAME>
2. Enter your GitHub username and password, if prompted to do so.
3. Create a Pull Request on GitHub.
4. Fill out the body of the Pull Request with information about the changes you’re introducing.
```

Information for developers:
```bash
$ git clone git@github.com:mgoldsmith1/Market-Exchange-Simulator.git
$ cd Market-Exchange-Simulator/osc-project-6
$ git status
$ git branch
$ git checkout -b update/XOPS-12345
(update a file)
$ git diff modified_file.txt (see the changes)
(add a file)
$ git add file.txt
$ git commit -a -m "Added a file and modified a file"
$ git push origin update/XOPS-12345
$ git status
```
