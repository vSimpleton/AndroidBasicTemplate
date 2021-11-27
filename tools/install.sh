#!/bin/sh
set -e

repo="https://github.com/vSimpleton/AndroidBasicTemplate"
project_name=""
package_name=""

function finish_with_code() {
    exit $@
}

cat <<'EOF'
                     _           _     _ 
     /\             | |         (_)   | |
    /  \   _ __   __| |_ __ ___  _  __| |
   / /\ \ | '_ \ / _` | '__/ _ \| |/ _` |
  / ____ \| | | | (_| | | | (_) | | (_| |
 /_/    \_\_| |_|\__,_|_|  \___/|_|\__,_|   _       _       
                 |__   __|                 | |     | |      
                    | | ___ _ __ ___  _ __ | | __ _| |_ ___ 
                    | |/ _ \ '_ ` _ \| '_ \| |/ _` | __/ _ \
                    | |  __/ | | | | | |_) | | (_| | ||  __/
                    |_|\___|_| |_| |_| .__/|_|\__,_|\__\___|
                                     | |                    
                                     |_|                    
EOF

# Parse arguments
while [ $# -gt 0 ]; do
    case $1 in
        --project) 
            project_name=$2 
            ;;
        --package)
            package_name=$2
            ;;
    esac
    shift
done

# Clone repo code
git clone -c core.autocrlf=false --depth=1 \
    --branch "master" ${repo} ./AndroidTemplate || {
    printf "git clone of ${repo} repo failed."
    finish_with_code 1
}

# Check arguments
if [ -z "${project_name}" ]; then
    read -t 300 -p "Input new project name: " project_name
    if [ -z "${project_name}" ]; then
        printf "project name can not empty."
        finish_with_code 1
    fi
fi

if [ -z "${package_name}" ]; then
    read -t 300 -p "Input package name: " package_name
    if [ -z "${package_name}" ]; then
        printf "package name can not empty."
        finish_with_code 1
    fi
fi

echo -e "\033[33m Init project...\n Project name: ${project_name}\n Package name: ${package_name} \033[m"

cd ./AndroidTemplate

mkdir -p ./app/src/main/java/${package_name//.//}
mkdir -p ./app/src/test/java/${package_name//.//}
mkdir -p ./app/src/androidTest/java/${package_name//.//}

mv ./app/src/main/java/com/vsimpleton/template/* ./app/src/main/java/${package_name//.//}
mv ./app/src/test/java/com/vsimpleton/template/* ./app/src/test/java/${package_name//.//}
mv ./app/src/androidTest/java/com/vsimpleton/template/* ./app/src/androidTest/java/${package_name//.//}

find ./app/src -depth -type d -empty -delete

echo "# ${project_name}" > ./README.md
str_default_name="AndroidBasicTemplate"
replace_project_name_cmd=s@${str_default_name}@${project_name}@

#for file in $(find . -regextype posix-extended -regex ".*\.(kt|xml|java|gradle)" -o -name "proguard-rules.pro");
for file in $(find . -name "*.kt" -o -name "*.xml" -o -name "*.java" -o -name "*.gradle" -o -name "proguard-rules.pro");
do
    sed -i.tmpbak "s/com.vsimpleton.template/${package_name}/g" ${file}
    sed -i.tmpbak "${replace_project_name_cmd}" ${file}
done

rm -rf ./.git ./tools
find . -name "*.tmpbak" | xargs rm -rf

cd ..
mv AndroidTemplate ${project_name}

echo -e "\033[32m ------ Completed! ------ \033[m"
finish_with_code 0