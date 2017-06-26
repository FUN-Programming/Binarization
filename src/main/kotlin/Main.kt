import processing.core.PApplet

class Main : PApplet() {

    override fun settings() {
        super.settings()
    }

    override fun setup() {
        super.setup()
        background(255)

        binarization("sample2.png", 0.9)
        binarization("sample3.png", 0.75)
        binarization("sample4.png", 0.85)
        binarization("ma.png", 0.5)

        exit()
    }

    fun getImageBrightness(fileName: String): Array<Int> {
        val pImage = loadImage(fileName).apply { loadPixels() }
        val brightness: Array<Int> = Array(256, { 0 })

        pImage.pixels.forEach { color ->
            brightness[brightness(color).toInt()]++
        }

        return brightness
    }

    fun getThreshold(fileName: String, rate: Double): Int {
        val pImage = loadImage(fileName)
        val brightness = getImageBrightness(fileName)
        val pixels = pImage.width * pImage.height
        var sum = 0

        brightness.forEachIndexed { index, i ->
            sum += i
            if (sum >= pixels * rate) return index
        }

        return -1
    }

    fun binarization(fileName: String, rate: Double) {
        val threshold = getThreshold(fileName, rate).apply { println("Threshold: $this") }
        val pImage = loadImage(fileName).apply { loadPixels() }

        pImage.pixels.forEachIndexed { index, color ->
            pImage.pixels[index] = color(if (brightness(color).toInt() > threshold) 255 else 0)
        }
        pImage.updatePixels()
        pImage.save(fileName.removeSuffix(".png") + "_out.png")
    }
}

fun main(args: Array<String>) {
    PApplet.main("Main")
}
