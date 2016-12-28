if [ 0 == 1 ]; then
	echo "Versioning désactivé"
	exit 
fi
function getver
{
	major=$(grep "define VERSION_MAJOR" "$1"  | awk '{print $3}')
	minor=$(grep "define VERSION_MINOR" "$1"  | awk '{print $3}')
	build=$(grep "define VERSION_BUILD" "$1"  | awk '{print $3}')
}

	md5_src="/tmp/version.md5_src"
	ls -1 *.cpp *.h *.ino | grep -v version.h | sort | xargs -l -i md5sum {} > "$md5_src"
	# cat "$md5_src"
	mdver=$(md5sum $md5_src | awk '{print $1}')
		# echo "MD5Ver = $mdver"

		vpath="versioning/"
		vfile="$vpath/$mdver"

		mkdir -p "$vpath"

		if [ -f "$vfile" ]; then
			getver $vfile
			cp -f $vfile version.h
			echo "Version already exists: $major.$minor.$build"
		else
			mkdir -p "$vpath/tar"
			echo "This version does not exists"
			if [ -f "$vpath/last_version.h" ]; then
				echo "Taking last version"
			else
				echo "Creating last version file"
				cp version.h "$vpath/last_version.h"
			fi
			getver "$vpath/last_version.h"
			new_major=$major
			new_minor=$minor
			new_build=$(expr $build + 1)
			if [ "$new_build" -gt 999 ]; then
				new_build=0
				echo "Increment minor"
				new_minor=$(expr $minor + 1)
				if [ "$new_minor" == "10" ]; then
					new_major=$(expr $major + 1)
					new_minor=0;
				fi
			fi
			cp "$vpath/last_version.h" "$vfile"
			sed -i "s/MAJOR $major/MAJOR $new_major/g" "$vfile"
			sed -i "s/MINOR $minor/MINOR $new_minor/g" "$vfile"
			sed -i "s/BUILD $build/BUILD $new_build/g" "$vfile"
			# cat "$vfile"

			cp -f "$vfile" version.h
			cp -f "$vfile" "$vpath/last_version.h"
			echo "Old Version $major.$minor.$build"
			echo "New Version $new_major.$new_minor.$new_build"
			tar zcf "$vpath/tar/$new_major.$new_minor.$new_build.tgz" *.cpp *.h *.ino *.txt
		fi

		last_mdver="$mdver"
