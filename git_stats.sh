#!/bin/bash

if [[ $1 == "" && $2 == "" ]]
then
   echo "USAGE: $0 SINCE_DATE [AUTHOR_PATTERN]"
   exit
fi

DATE=$1 # 10-1-2011, 2012-04-22, etc
AUTHOR=$2 # author pattern (regexp)

if [[ $AUTHOR ]]
then
   # detailed stats for an individual 
   git log --shortstat --author="$AUTHOR" --since=$DATE | grep "files changed" | awk '{files+=$1; inserted+=$4; deleted+=$6} END {print "files changed: ", files, "\nlines inserted: ", inserted, "\nlines deleted: ", deleted}'
fi

# commit numbers by author for the repo
git log --pretty=format:%an --since=$DATE | awk '{ ++c[$0]; } END { for(cc in c) printf "%5d %s\n",c[cc],cc; }' | sort -r

# detailed stats per author, including contribution to the total change
git log --numstat --since=$DATE | awk '
function printStats(author) {
   printf "%s:\n", author
   printf "  insertions: %d  (%.0f%%)\n", more[author], (more[author] / more["total"] * 100)
   printf "  deletions: %d  (%.0f%%)\n", less[author], (less[author] / less["total"] * 100)
   printf "  files: %d  (%.0f%%)\n", file[author], (file[author] / file["total"] * 100)
   printf "  commits: %d  (%.0f%%)\n", commits[author], (commits[author] / commits["total"] * 100)
}

/^Author:/ {
   author           = $2 " " $3
   commits[author] += 1
   commits["total"]  += 1
}

/^[0-9]/ {
   more[author] += $1
   less[author] += $2
   file[author] += 1

   more["total"]  += $1
   less["total"]  += $2
   file["total"]  += 1
}

END {
   for (author in commits) {
      if (author != "total") {
         printStats(author)
      }
   }
   printStats("total")
}'