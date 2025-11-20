class Matrix
  attr_reader :rows, :cols, :data

  def initialize(rows, cols)
    @rows = rows
    @cols = cols
    @data = Array.new(rows) { Array.new(cols, 0.0) }
  end

  def set_element(i, j, value)
    @data[i][j] = value.to_f
  end

  def get_element(i, j)
    @data[i][j]
  end

  def self.add(m1, m2)
    if m1.rows != m2.rows || m1.cols != m2.cols
      raise ArgumentError, 
        "Matrix dimensions must match for addition. " +
        "Matrix 1: #{m1.rows}x#{m1.cols}, " +
        "Matrix 2: #{m2.rows}x#{m2.cols}"
    end

    result = Matrix.new(m1.rows, m1.cols)
    m1.rows.times do |i|
      m1.cols.times do |j|
        result.data[i][j] = m1.data[i][j] + m2.data[i][j]
      end
    end
    result
  end

  def self.subtract(m1, m2)
    if m1.rows != m2.rows || m1.cols != m2.cols
      raise ArgumentError,
        "Matrix dimensions must match for subtraction. " +
        "Matrix 1: #{m1.rows}x#{m1.cols}, " +
        "Matrix 2: #{m2.rows}x#{m2.cols}"
    end

    result = Matrix.new(m1.rows, m1.cols)
    m1.rows.times do |i|
      m1.cols.times do |j|
        result.data[i][j] = m1.data[i][j] - m2.data[i][j]
      end
    end
    result
  end

  def self.multiply(m1, m2)
    if m1.cols != m2.rows
      raise ArgumentError,
        "Matrix 1 columns must equal Matrix 2 rows for multiplication. " +
        "Matrix 1: #{m1.rows}x#{m1.cols}, " +
        "Matrix 2: #{m2.rows}x#{m2.cols}"
    end

    result = Matrix.new(m1.rows, m2.cols)
    m1.rows.times do |i|
      m2.cols.times do |j|
        sum = 0.0
        m1.cols.times do |k|
          sum += m1.data[i][k] * m2.data[k][j]
        end
        result.data[i][j] = sum
      end
    end
    result
  end

  def fill_random
    @rows.times do |i|
      @cols.times do |j|
        @data[i][j] = rand * 100
      end
    end
  end

  def print_matrix
    @rows.times do |i|
      row = ""
      @cols.times do |j|
        row += sprintf("%.2f\t", @data[i][j])
      end
      puts row
    end
  end
end

class MatrixOperations
  def initialize
  end

  def run
    running = true

    while running
      display_menu
      choice = get_int_input("Enter your choice: ")

      if choice == 4
        running = false
        puts "Exiting program. Goodbye!"
        next
      end

      begin
        m1 = input_matrix("Matrix 1")
        m2 = input_matrix("Matrix 2")

        result = nil
        operation = ""

        case choice
        when 1
          result = Matrix.add(m1, m2)
          operation = "Addition"
        when 2
          result = Matrix.subtract(m1, m2)
          operation = "Subtraction"
        when 3
          result = Matrix.multiply(m1, m2)
          operation = "Multiplication"
        else
          puts "Invalid choice!"
          wait_for_user
          next
        end

        puts "\n#{operation} Result:"
        result.print_matrix

      rescue ArgumentError => e
        puts "\nError: #{e.message}"
      rescue => e
        puts "\nError: #{e.message}"
      end

      wait_for_user
    end
  end

  private

  def display_menu
    puts "\n" + "=" * 50
    puts "MATRIX OPERATIONS MENU (Ruby Version)"
    puts "=" * 50
    puts "1. Matrix Addition"
    puts "2. Matrix Subtraction"
    puts "3. Matrix Multiplication"
    puts "4. Exit"
    puts "=" * 50
  end

  def input_matrix(name)
    puts "\n#{name} Input:"
    puts "1. Enter manually"
    puts "2. Read from file"
    choice = get_int_input("Choose input method: ")

    if choice == 1
      input_matrix_manually
    elsif choice == 2
      read_matrix_from_file
    else
      raise ArgumentError, "Invalid input method!"
    end
  end

  def input_matrix_manually
    rows = get_int_input("Enter number of rows: ")
    cols = get_int_input("Enter number of columns: ")

    matrix = Matrix.new(rows, cols)

    puts "Enter matrix elements row by row:"
    rows.times do |i|
      cols.times do |j|
        value = get_float_input("Element [#{i}][#{j}]: ")
        matrix.set_element(i, j, value)
      end
    end

    matrix
  end

  def read_matrix_from_file
    print "Enter filename: "
    filename = gets.chomp

    unless File.exist?(filename)
      raise IOError, "File '#{filename}' not found"
    end

    lines = File.readlines(filename).map(&:strip)
    
    dimensions = lines[0].split
    rows = dimensions[0].to_i
    cols = dimensions[1].to_i

    matrix = Matrix.new(rows, cols)

    rows.times do |i|
      elements = lines[i + 1].split
      cols.times do |j|
        matrix.set_element(i, j, elements[j].to_f)
      end
    end

    puts "Matrix loaded successfully from #{filename}"
    matrix
  end

  def performance_test
    puts "\n" + "=" * 50
    puts "PERFORMANCE TEST: 10x10 Matrix Multiplication"
    puts "=" * 50

    m1 = Matrix.new(10, 10)
    m2 = Matrix.new(10, 10)

    m1.fill_random
    m2.fill_random

    puts "Matrices populated with random values..."

    start_time = Time.now
    result = Matrix.multiply(m1, m2)
    end_time = Time.now

    duration = (end_time - start_time) * 1000

    puts "\nTime taken: #{duration.round(4)} milliseconds"
    puts "Time taken: #{(duration * 1000).round(4)} microseconds"

    puts "\nFirst 5x5 of result matrix:"
    [5, result.rows].min.times do |i|
      row = ""
      [5, result.cols].min.times do |j|
        row += sprintf("%.2f\t", result.get_element(i, j))
      end
      puts row
    end
  end

  def get_int_input(prompt)
    loop do
      print prompt
      input = gets.chomp
      
      if input.match?(/^-?\d+$/)
        return input.to_i
      else
        puts "Invalid input! Please enter an integer."
      end
    end
  end

  def get_float_input(prompt)
    loop do
      print prompt
      input = gets.chomp
      
      if input.match?(/^-?\d+\.?\d*$/)
        return input.to_f
      else
        puts "Invalid input! Please enter a number."
      end
    end
  end

  def wait_for_user
    puts "\nPress Enter to continue..."
    gets
  end
end

# Main execution
if __FILE__ == $0
  app = MatrixOperations.new
  app.run
end