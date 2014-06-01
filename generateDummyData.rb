#!/usr/bin/env ruby
actions = ['selling', 'buying']
dates = ['2014/06/01',
               '2014/05/31',
               '2014/05/30',
               '2014/05/29',
               '2014/05/28',
               '2014/05/27',
               '2014/05/26',
               '2014/05/25',
               '2014/05/24',
               '2014/05/23',
               '2014/05/22',
               '2014/05/21',
               '2014/05/20']

def generateRandomCost(base)
  base + (rand(0..base) * (-0.1) ** rand(1..2))
end

for i in 0..1000
  print [dates.sample, actions.sample, generateRandomCost(400)].join(" ") + "\n"
end