to list all images:
>docker images

to delete an image by its image ID:
>docker rmi [IMAGE ID]

deleting all orphaned images:
>docker image prune

can do the same thing for containers:
>docker container prune

to force a clean build (sometimes stdout is cached!)
>docker build --no-cache

to show the STDOUT of the docker build process:
>docker build --progress=plain --no-cache

run a command and only show its output if it err:
>RUN output=`[ORIGINAL COMMAND] 2>&1` || echo $output

