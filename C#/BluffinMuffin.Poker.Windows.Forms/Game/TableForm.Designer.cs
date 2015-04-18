using System.Windows.Forms;

namespace BluffinMuffin.Poker.Windows.Forms.Game
{
    partial class TableForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TableForm));
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.btnFold = new Button();
            this.btnCall = new Button();
            this.btnRaise = new Button();
            this.nudRaise = new System.Windows.Forms.NumericUpDown();
            this.btnSitOut = new Button();
            this.btnSitIn0 = new Button();
            this.btnSitIn1 = new Button();
            this.btnSitIn2 = new Button();
            this.btnSitIn3 = new Button();
            this.btnSitIn4 = new Button();
            this.btnSitIn5 = new Button();
            this.btnSitIn6 = new Button();
            this.btnSitIn7 = new Button();
            this.btnSitIn8 = new Button();
            this.btnSitIn9 = new Button();
            this.flowLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudRaise)).BeginInit();
            this.SuspendLayout();
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.Controls.Add(this.btnFold);
            this.flowLayoutPanel1.Controls.Add(this.btnCall);
            this.flowLayoutPanel1.Controls.Add(this.btnRaise);
            this.flowLayoutPanel1.Controls.Add(this.nudRaise);
            this.flowLayoutPanel1.Controls.Add(this.btnSitOut);
            this.flowLayoutPanel1.FlowDirection = System.Windows.Forms.FlowDirection.TopDown;
            this.flowLayoutPanel1.Location = new System.Drawing.Point(874, 52);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(146, 501);
            this.flowLayoutPanel1.TabIndex = 3;
            // 
            // btnFold
            // 
            this.btnFold.Enabled = false;
            this.btnFold.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnFold.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.leave;
            this.btnFold.Location = new System.Drawing.Point(5, 5);
            this.btnFold.Margin = new System.Windows.Forms.Padding(5);
            this.btnFold.Name = "btnFold";
            this.btnFold.Size = new System.Drawing.Size(135, 23);
            this.btnFold.TabIndex = 0;
            this.btnFold.Text = "FOLD";
            this.btnFold.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnFold.UseVisualStyleBackColor = true;
            this.btnFold.Click += new System.EventHandler(this.btnFold_Click);
            // 
            // btnCall
            // 
            this.btnCall.Enabled = false;
            this.btnCall.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnCall.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.cards_mini;
            this.btnCall.Location = new System.Drawing.Point(5, 38);
            this.btnCall.Margin = new System.Windows.Forms.Padding(5);
            this.btnCall.Name = "btnCall";
            this.btnCall.Size = new System.Drawing.Size(135, 23);
            this.btnCall.TabIndex = 1;
            this.btnCall.Text = "CALL";
            this.btnCall.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnCall.UseVisualStyleBackColor = true;
            this.btnCall.Click += new System.EventHandler(this.btnCall_Click);
            // 
            // btnRaise
            // 
            this.btnRaise.Enabled = false;
            this.btnRaise.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnRaise.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.money;
            this.btnRaise.Location = new System.Drawing.Point(5, 71);
            this.btnRaise.Margin = new System.Windows.Forms.Padding(5);
            this.btnRaise.Name = "btnRaise";
            this.btnRaise.Size = new System.Drawing.Size(135, 23);
            this.btnRaise.TabIndex = 2;
            this.btnRaise.Text = "RAISE";
            this.btnRaise.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnRaise.UseVisualStyleBackColor = true;
            this.btnRaise.Click += new System.EventHandler(this.btnRaise_Click);
            // 
            // nudRaise
            // 
            this.nudRaise.Enabled = false;
            this.nudRaise.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.nudRaise.Location = new System.Drawing.Point(5, 104);
            this.nudRaise.Margin = new System.Windows.Forms.Padding(5);
            this.nudRaise.Name = "nudRaise";
            this.nudRaise.Size = new System.Drawing.Size(135, 26);
            this.nudRaise.TabIndex = 3;
            this.nudRaise.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // btnSitOut
            // 
            this.btnSitOut.Enabled = false;
            this.btnSitOut.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitOut.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_out;
            this.btnSitOut.Location = new System.Drawing.Point(5, 140);
            this.btnSitOut.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitOut.Name = "btnSitOut";
            this.btnSitOut.Size = new System.Drawing.Size(135, 23);
            this.btnSitOut.TabIndex = 5;
            this.btnSitOut.Text = "SIT OUT";
            this.btnSitOut.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitOut.UseVisualStyleBackColor = true;
            this.btnSitOut.Click += new System.EventHandler(this.btnSitOut_Click);
            // 
            // btnSitIn0
            // 
            this.btnSitIn0.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn0.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_in;
            this.btnSitIn0.Location = new System.Drawing.Point(211, 31);
            this.btnSitIn0.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn0.Name = "btnSitIn0";
            this.btnSitIn0.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn0.TabIndex = 4;
            this.btnSitIn0.Text = "SIT IN";
            this.btnSitIn0.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn0.UseVisualStyleBackColor = true;
            this.btnSitIn0.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // btnSitIn1
            // 
            this.btnSitIn1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn1.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_in;
            this.btnSitIn1.Location = new System.Drawing.Point(363, 31);
            this.btnSitIn1.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn1.Name = "btnSitIn1";
            this.btnSitIn1.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn1.TabIndex = 6;
            this.btnSitIn1.Text = "SIT IN";
            this.btnSitIn1.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn1.UseVisualStyleBackColor = true;
            this.btnSitIn1.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // btnSitIn2
            // 
            this.btnSitIn2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn2.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_in;
            this.btnSitIn2.Location = new System.Drawing.Point(516, 31);
            this.btnSitIn2.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn2.Name = "btnSitIn2";
            this.btnSitIn2.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn2.TabIndex = 7;
            this.btnSitIn2.Text = "SIT IN";
            this.btnSitIn2.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn2.UseVisualStyleBackColor = true;
            this.btnSitIn2.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // btnSitIn3
            // 
            this.btnSitIn3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn3.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_in;
            this.btnSitIn3.Location = new System.Drawing.Point(705, 184);
            this.btnSitIn3.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn3.Name = "btnSitIn3";
            this.btnSitIn3.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn3.TabIndex = 8;
            this.btnSitIn3.Text = "SIT IN";
            this.btnSitIn3.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn3.UseVisualStyleBackColor = true;
            this.btnSitIn3.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // btnSitIn4
            // 
            this.btnSitIn4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn4.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_in;
            this.btnSitIn4.Location = new System.Drawing.Point(705, 335);
            this.btnSitIn4.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn4.Name = "btnSitIn4";
            this.btnSitIn4.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn4.TabIndex = 9;
            this.btnSitIn4.Text = "SIT IN";
            this.btnSitIn4.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn4.UseVisualStyleBackColor = true;
            this.btnSitIn4.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // btnSitIn5
            // 
            this.btnSitIn5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn5.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_in;
            this.btnSitIn5.Location = new System.Drawing.Point(516, 485);
            this.btnSitIn5.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn5.Name = "btnSitIn5";
            this.btnSitIn5.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn5.TabIndex = 10;
            this.btnSitIn5.Text = "SIT IN";
            this.btnSitIn5.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn5.UseVisualStyleBackColor = true;
            this.btnSitIn5.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // btnSitIn6
            // 
            this.btnSitIn6.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn6.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_in;
            this.btnSitIn6.Location = new System.Drawing.Point(363, 485);
            this.btnSitIn6.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn6.Name = "btnSitIn6";
            this.btnSitIn6.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn6.TabIndex = 11;
            this.btnSitIn6.Text = "SIT IN";
            this.btnSitIn6.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn6.UseVisualStyleBackColor = true;
            this.btnSitIn6.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // btnSitIn7
            // 
            this.btnSitIn7.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn7.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_in;
            this.btnSitIn7.Location = new System.Drawing.Point(211, 485);
            this.btnSitIn7.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn7.Name = "btnSitIn7";
            this.btnSitIn7.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn7.TabIndex = 12;
            this.btnSitIn7.Text = "SIT IN";
            this.btnSitIn7.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn7.UseVisualStyleBackColor = true;
            this.btnSitIn7.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // btnSitIn8
            // 
            this.btnSitIn8.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn8.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_in;
            this.btnSitIn8.Location = new System.Drawing.Point(26, 335);
            this.btnSitIn8.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn8.Name = "btnSitIn8";
            this.btnSitIn8.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn8.TabIndex = 13;
            this.btnSitIn8.Text = "SIT IN";
            this.btnSitIn8.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn8.UseVisualStyleBackColor = true;
            this.btnSitIn8.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // btnSitIn9
            // 
            this.btnSitIn9.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn9.Image = global::BluffinMuffin.Poker.Windows.Forms.Properties.Resources.sit_in;
            this.btnSitIn9.Location = new System.Drawing.Point(26, 184);
            this.btnSitIn9.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn9.Name = "btnSitIn9";
            this.btnSitIn9.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn9.TabIndex = 14;
            this.btnSitIn9.Text = "SIT IN";
            this.btnSitIn9.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn9.UseVisualStyleBackColor = true;
            this.btnSitIn9.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // TableForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1018, 687);
            this.Controls.Add(this.btnSitIn1);
            this.Controls.Add(this.btnSitIn2);
            this.Controls.Add(this.btnSitIn3);
            this.Controls.Add(this.btnSitIn4);
            this.Controls.Add(this.btnSitIn5);
            this.Controls.Add(this.btnSitIn6);
            this.Controls.Add(this.btnSitIn7);
            this.Controls.Add(this.btnSitIn8);
            this.Controls.Add(this.btnSitIn9);
            this.Controls.Add(this.btnSitIn0);
            this.Controls.Add(this.flowLayoutPanel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "TableForm";
            this.Text = "C# Poker Table";
            this.Controls.SetChildIndex(this.flowLayoutPanel1, 0);
            this.Controls.SetChildIndex(this.btnSitIn0, 0);
            this.Controls.SetChildIndex(this.btnSitIn9, 0);
            this.Controls.SetChildIndex(this.btnSitIn8, 0);
            this.Controls.SetChildIndex(this.btnSitIn7, 0);
            this.Controls.SetChildIndex(this.btnSitIn6, 0);
            this.Controls.SetChildIndex(this.btnSitIn5, 0);
            this.Controls.SetChildIndex(this.btnSitIn4, 0);
            this.Controls.SetChildIndex(this.btnSitIn3, 0);
            this.Controls.SetChildIndex(this.btnSitIn2, 0);
            this.Controls.SetChildIndex(this.btnSitIn1, 0);
            this.flowLayoutPanel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.nudRaise)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        private Button btnFold;
        private Button btnCall;
        private Button btnRaise;
        private System.Windows.Forms.NumericUpDown nudRaise;
        private Button btnSitIn0;
        private Button btnSitOut;
        private Button btnSitIn1;
        private Button btnSitIn2;
        private Button btnSitIn3;
        private Button btnSitIn4;
        private Button btnSitIn5;
        private Button btnSitIn6;
        private Button btnSitIn7;
        private Button btnSitIn8;
        private Button btnSitIn9;


    }
}