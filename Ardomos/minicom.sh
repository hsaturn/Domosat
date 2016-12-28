while [ 1 == 1 ]; do
	minicom -D /dev/ttyUSB0 -b 57600
	sleep 3
	loop=0
	while [ "$loop" == "0" ]; do
		ps -ef | grep avrdude | grep -v grep >/dev/null 2>&1
		loop=$?
		sleep 1
		echo "Waiting for avrdude..."
	done
done
