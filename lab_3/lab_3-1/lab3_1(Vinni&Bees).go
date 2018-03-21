// lab3_1(Vinni&Beens)
package main

import (
	"fmt"
	"time"
)

func main() {
	var n int
	var h int
	for n <= 0 {
		fmt.Println("Enter the number of Bees: ")
		fmt.Scan(&n)
	}
	for h <= 0 {
		fmt.Println("Enter the number of Swallows: ")
		fmt.Scan(&h)
	}

	demo(n, h)

	var input string
	fmt.Scanln(&input)
}

func demo(n int, h int) {

	channel := make(chan []bool)
	go vinni(channel)
	for i := 0; i < n; i++ {
		go workingBee(channel, make([]bool, 0), h)
	}

	var input string
	fmt.Scanln(&input)
}

func workingBee(channel chan []bool, honey []bool, sws int) {
	for true {
		if len(honey) >= sws {
			channel <- honey
			honey = <-channel
		} else {
			honey = append(honey, true)
		}

		time.Sleep(time.Second * 2)
	}
}

func vinni(channel chan []bool) {
	for true {
		var sws int
		fmt.Println("Bees are gathering honey...")
		honey := <-channel
		sws = len(honey)
		channel <- make([]bool, 0)
		fmt.Println("Vinni ate ", sws, "swallows of honey!\n")

	}
}
