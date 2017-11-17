# XML versions of the same file [fileName].xml will be saved as [fileName] ([x]).xml
# The point of this script is to find the newest one in the set that contains these, and [filename].xml, and delete the rest in that set.

# scan for unique XML files, ignoring the "([x])".
#ls ./* | cut -d '.' -f 1,2,3 | sort -u	# test line found from https://unix.stackexchange.com/questions/224260/get-list-of-files-which-are-unique
find . -maxdepth 1 -type f	# -regex might be your friend here
