// lab3-2
package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	var n int
	for n <= 0 {
		fmt.Println("Enter the number of clients: ")
		fmt.Scan(&n)
	}
	demo(n)

	var input string
	fmt.Scanln(&input)
}

func demo(colClts int) {
	clientToBarber := make(chan int)
	barberToClient := make(chan bool)

	go barber(clientToBarber, barberToClient)

	for i := 0; i < colClts; i++ {
		time.Sleep(time.Second * time.Duration(rand.Intn(20)))
		go client(clientToBarber, barberToClient)
	}

	var input string
	fmt.Scanln(&input)

}

func barber(chanFromClts chan int, chanToClts chan bool) {
	for true {
		fmt.Println("Barber is sleeping")
		duration := <-chanFromClts
		fmt.Println("Barber wakes up")
		fmt.Print("Barber is working")
		for j := 0; j <= duration; j++ {
			time.Sleep(time.Second)
			fmt.Print(". ")
		}
		fmt.Println(" ")
		chanToClts <- true
	}
}

func client(chanToBarber chan int, chanFromBarber chan bool) {
	var duration int
	duration = rand.Intn(20)
	chanToBarber <- duration
	finished := <-chanFromBarber

	for finished != true {
		chanFromBarber <- finished
	}
}
